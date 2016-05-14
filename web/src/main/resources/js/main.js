/**
 * Created by Андрей on 27.02.2016.
 */
var app = angular.module('app', [
    'ui.router',
    'ui.bootstrap',
    'ngStorage',
    'loginControllers',
    'loginServices',
    'adminControllers',
    'adminServices',
    'door3.css',
    'studyControllers',
    'studyServices'
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
        .state('groups', {
            url: '/groups',
            templateUrl: 'partial/admin/groups.html',
            controller: 'groupsCtrl'
        })
        .state('rights', {
            url: '/rights/{id:[0-9]+}',
            templateUrl: 'partial/admin/rights.html',
            controller: 'rightsCtrl'
        })
        .state('services', {
            url: '/services',
            templateUrl: 'partial/admin/services.html',
            controller: 'servicesCtrl'
        })
        .state('study', {
            url: '/studyplan',
            templateUrl: 'partial/study/study.html',
            controller: 'studyCtrl'
        })
        .state('revisions', {
            url: '/revisions/{fileId}/{name}',
            templateUrl: 'partial/study/revisions.html',
            controller: 'revisionsCtrl'
        })
        .state('error',{
            url: '/error',
            templateUrl: 'partial/error.html'
        });

});

function replacer(key,value){
    if (key=="$$hashKey") return undefined;
    else return value;
}

function userReplacer(key,value){
    if (key=="$$hashKey") return undefined;
    if (key=="added") return undefined;
    return value;
}

getDropDown = function (attrs) {
    var result = '<select class="selectpicker">';
    attrs.forEach(function(item, i, arr) {
        var elem = "<option value=" + item.id +">" + item.name + "</option>";
        result += elem;
    });
    result += "</select>";
    return result;
}

getInputPair = function(attrs) {
    var attr = getDropDown(attrs);
    var result = "<tr><td>";
    result += attr;
    result += "</td><td>";
    result += '<input class="form-control inputattr" type="text">';
    result += "</td></tr>";
    return result;
}

getAttributeString = function() {
    var selectsAll = document.getElementsByTagName("select");
    var selects = Object.keys(selectsAll);
    var valsAll = document.getElementsByClassName("inputattr");
    var vals = Object.keys(valsAll);
    var result = {};
    for (var i = 0; i < selects.length; i++) {
        result[selectsAll[selects[i]].value] = valsAll[vals[i]].value;
    }
    return JSON.stringify(result, replacer);
}
