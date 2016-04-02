/**
 * Created by Андрей on 07.03.2016.
 */
function showSubs() {
    $('#tabs li:eq(1) a').tab('show');
}
var studyControllers = angular.module('studyControllers', []);

studyControllers.controller('studyCtrl', ['$scope', 'studyRepository', '$state', '$localStorage',
    function ($scope, studyRepository, $state, $localStorage) {
        studyRepository.getAllFiles($localStorage.sessionKey)
            .success(function (data) {
                $scope.files = data;
            })
            .error(function (data) {
                $state.go("error");
            });

        //studyRepository.getUserInfo($localStorage.sessionKey)
        //    .success(function (data) {
        //        $scope.name = data.name;
        //    })
        //    .error(function (data) {
        //        $state.go("error");
        //    });

        studyRepository.getAllFiles($localStorage.sessionKey)
            .success(function (data) {
                $scope.files = data;
            });

        //$scope.choose = function(user){
        //    $scope.curUser = user;
        //    adminRepository.getUserServices($localStorage.sessionKey, user.login)
        //        .success(function (data) {
        //            $scope.curUser.services = [];
        //            data.forEach(function(item, i, data){
        //                $scope.curUser.services.push(item);
        //            });
        //        })
        //    adminRepository.getUserGroups($localStorage.sessionKey, user.login)
        //        .success(function (data) {
        //            $scope.curUser.groups = [];
        //            data.forEach(function(item, i, data){
        //                $scope.curUser.groups.push(item);
        //            });
        //        })
        //}



        //$scope.login = function(loginForm) {
        //    if (loginForm.$valid) {
        //        userRepository.login($scope.user.login,
        //            $scope.user.token)
        //            .success(function (data) {
        //                $localStorage.sessionKey = data.result;
        //                $state.go("home");
        //            })
        //            .error(function () {
        //                var element = document.getElementById("login-err");
        //                var element1 = document.getElementsByClassName("login-msg")[0];
        //                element.innerHTML = "Неверное имя пользователя или пароль";
        //                element1.style.display = "block";
        //            });
        //    }
        //}
    }]);