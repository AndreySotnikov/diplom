/**
 * Created by Андрей on 28.02.2016.
 */
var loginControllers = angular.module('loginControllers', []);

loginControllers.controller('loginCtrl', ['$scope', '$http',
    function ($scope, $http) {
        //$http.get('phones/phones.json').success(function(data) {
        //    $scope.phones = data;
        //});
        $scope.greeting = 'login';
    }]);

loginControllers.controller('registrationCtrl', ['$scope', '$http','User',
    function($scope,$http, User) {
        $scope.register = function(){
            //$http({
            //    url: "https://localhost:8082/register",
            //    method: "POST",
            //    data: "login="+ $scope.user.login+"&token="+$scope.user.token,
            //    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            //}).success(function(data){
            //    $scope.greeting = data;
            //});
            User.register({login:$scope.user.login, token: $scope.user.token}, function(result){
                $scope.greeting = result;
            });
        };

    }]);