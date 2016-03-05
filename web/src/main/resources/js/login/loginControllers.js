/**
 * Created by Андрей on 28.02.2016.
 */
var loginControllers = angular.module('loginControllers', []);

loginControllers.controller('loginCtrl', ['$scope', 'userRepository', '$state', '$localStorage',
    function ($scope, userRepository, $state, $localStorage) {
        $scope.login = function() {
            userRepository.login($scope.user.login,
                $scope.user.token)
                .success(function (data) {
                    $localStorage.sessionKey = data.result;
                    $state.go("home");
                })
                .error(function (data){
                    $state.go("error");
                });
        }
    }]);

loginControllers.controller('registrationCtrl', ['$scope', '$http','User',
    function($scope,$http, User) {
        $scope.register = function(){
            User.register({login:$scope.user.login, token: $scope.user.token}, function(result){
                $scope.greeting = result;
            });
        };

    }]);