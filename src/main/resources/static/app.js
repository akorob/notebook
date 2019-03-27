angular.module('Notebook', [])

    /* @ngInject */
    .controller('AppController', function ($scope, $http) {

        var NOTES_URL = 'http://localhost:8080/note';
        $scope.noteToAdd = {};
        $scope.notes = [];

        $scope.search = search;
        $scope.add = add;
        $scope.remove = remove;

        search();

        function remove(note) {
            return $http.put(NOTES_URL + '/delete', {id: note.id})
                .then(function (value) {
                    search($scope.filterStr);
                }, errorCallback);
        }

        function errorCallback(data, status) {
            console.log("errorCallback", data, status);
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