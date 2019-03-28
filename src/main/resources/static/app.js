angular.module('Notebook', [])

/* @ngInject */
    .controller('AppController', function ($scope, $http, $timeout) {

        var NOTES_URL = 'http://localhost:8080/note';
        $scope.noteToAdd = {};
        $scope.notes = [];
        $scope.errorMessage = "";
        $scope.filterStr = "";

        $scope.search = search;
        $scope.add = add;
        $scope.remove = remove;
        $scope.clear = clear;

        $scope.pagination = {
            prev: function () {
                $scope.pagination.page--;
                search();
            },
            next: function () {
                $scope.pagination.page++;
                search();
            },
            prevAvailable: function () {
                return $scope.pagination.page > 1;
            },
            nextAvailable: function () {
                return $scope.pagination.total > $scope.pagination.itemsPerPage * $scope.pagination.page;
            },
            page: 1,
            itemsPerPage: 5,
            total: 0
        };

        search();

        function clear() {
            $scope.errorMessage = "";
            $scope.filterStr = "";
            clearPagingAndSearch();
        }

        function remove(note) {
            return $http.put(NOTES_URL + '/delete', {id: note.id})
                .then(function (value) {
                    search($scope.filterStr);
                }, errorCallback);
        }

        //simple server error handling
        function errorCallback(response) {
            console.log("errorCallback", response);
            $scope.errorMessage = response.data.message;
            $timeout(function () {
                $scope.errorMessage = "";
            }, 2000);
        }

        function search(searchStr) {
            return $http.post(NOTES_URL + '/search', {
                searchString: searchStr,
                page: $scope.pagination.page,
                itemsPerPage: $scope.pagination.itemsPerPage
            })
                .then(function (response) {
                    $scope.notes = response.data.items;
                    $scope.pagination.total = response.data.count;
                }, errorCallback);
        }

        function clearPagingAndSearch() {
            $scope.pagination.page = 1;
            $scope.pagination.total = 0;
            search();
        }

        function add(noteToAdd) {
            return $http.post(NOTES_URL + '/add', noteToAdd)
                .then(function (value) {
                    $scope.noteToAdd = {};
                    search($scope.filterStr);
                }, errorCallback);
        }
    });