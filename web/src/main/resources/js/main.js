/**
 * Created by Андрей on 27.02.2016.
 */
var app = angular.module('app', [
    'ngRoute',
    'loginControllers',
    'loginServices'
]);
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
});
app.config(['$routeProvider',function($routeProvider){
    $routeProvider.
        when('/login',{
            templateUrl: 'partial/login/login.html',
            controller: 'loginCtrl'
        })
        .when('/register',{
            templateUrl: 'partial/login/register.html',
            controller: 'registrationCtrl'
        }).
        otherwise({
            redirectTo: '/error'
    });
}]);

angular.module('HelloWorldApp', [])
    .controller('HelloWorldController', function($scope) {
        $scope.greeting = "Hello World";
    });