/**
 * Created by Андрей on 28.02.2016.
 */
var loginControllers = angular.module('loginControllers', []);

loginControllers.controller('loginCtrl', ['$scope', 'userRepository', '$state', '$localStorage',
    function ($scope, userRepository, $state, $localStorage) {
        $scope.login = function(loginForm) {
            if (loginForm.$valid) {
                userRepository.login($scope.user.login,
                    $scope.user.token)
                    .success(function (data) {
                        $localStorage.sessionKey = data.result;
                        $state.go("home");
                    })
                    .error(function () {
                        var element = document.getElementById("login-err");
                        var element1 = document.getElementsByClassName("login-msg")[0];
                        element.innerHTML = "Неверное имя пользователя или пароль";
                        element1.style.display = "block";
                    });
            }
        }
    }]);

loginControllers.controller('registrationCtrl', ['$scope', 'userRepository', '$state',
    function($scope, userRepository, $state) {
        $scope.register = function(registrationForm){
            if (registrationForm.$valid) {
                userRepository.register($scope.user.login,
                    $scope.user.token, $scope.user.name,
                    $scope.user.email, $scope.user.phone)
                    .success(function () {
                        $state.go("login");
                    })
                    .error(function () {
                        var element = document.getElementById("register-err");
                        var element1 = document.getElementsByClassName("login-msg")[0];
                        element.innerHTML = "Пользователь с таким логином уже существует";
                        element1.style.display = "block";
                    })
            }
        };

    }]);