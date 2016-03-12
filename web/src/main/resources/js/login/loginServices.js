/**
 * Created by Андрей on 28.02.2016.
 */
var loginServices = angular.module('loginServices', ['ngResource']);

loginServices.factory('userRepository', ['$http', function ($http) {
    var userRepository = {};
    userRepository.login = function (login, token) {
        return $http({
            url: 'https://localhost:8082/login',
            method: "POST",
            data: "login=" + login +
            "&token=" + token,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    };
    userRepository.register = function (login, token, name, email, phone) {
        return $http({
            url: 'https://localhost:8082/register',
            method: "POST",
            data: "login=" + login +
            "&token=" + token +
            "&name=" + name +
            "&email=" + email +
            "&phone=" + phone,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
    };
    return userRepository;
}]);