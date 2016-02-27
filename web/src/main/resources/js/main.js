/**
 * Created by Андрей on 27.02.2016.
 */
angular.module('HelloWorldApp', [])
    .controller('HelloWorldController', function($scope) {
        $scope.greeting = "Hello World";
    });