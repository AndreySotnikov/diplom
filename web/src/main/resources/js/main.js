/**
 * Created by Андрей on 27.02.2016.
 */
var app = angular.module('app', [
    'ui.router',
    'ngStorage',
    'loginControllers',
    'loginServices',
    'adminControllers',
    'adminServices',
    'door3.css'
]);
app.config(function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
});
//app.config(['$routeProvider',function($routeProvider){
//    $routeProvider.
//        when('/login',{
//            templateUrl: 'partial/login/login.html',
//            controller: 'loginCtrl'
//        })
//        .when('/register',{
//            templateUrl: 'partial/login/register.html',
//            controller: 'registrationCtrl'
//        }).
//        otherwise({
//            redirectTo: '/error'
//    });
//}]);

app.config(function ($stateProvider) {
    $stateProvider
        .state('login', {
                url: '/login',
                templateUrl: 'partial/login/login.html',
                controller: 'loginCtrl'
        })
        .state('registration',{
                url: '/register',
                templateUrl: 'partial/login/register.html',
                controller: 'registrationCtrl'
        })
        .state('home', {
            url: '/home',
            templateUrl: 'partial/home.html'
        })
        .state('admin', {
            url: '/admin',
            templateUrl: 'admin.html',
            controller: 'adminCtrl'
        })
        .state('error',{
            url: '/error',
            templateUrl: 'partial/error.html'
        });

});
