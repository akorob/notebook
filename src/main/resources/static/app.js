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

        search();

        function clear() {
            $scope.errorMessage = "";
            $scope.filterStr = "";
            search();
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
            return $http.post(NOTES_URL + '/search', {searchString: searchStr})
                .then(function (response) {
                    $scope.notes = response.data.items;
                }, errorCallback);
        }

        function add(noteToAdd){
            return $http.post(NOTES_URL + '/add', noteToAdd)
                .then(function (value) {
                    $scope.noteToAdd = {};
                    search($scope.filterStr);
                }, errorCallback);
        }
    });