/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
var bankModule = angular.module('bankCtrl', ['gateway']);

bankModule
        .controller('registerCtrl', ['$scope', 'userGateway', function ($scope, userGateway) {
          'use strict';

          $scope.lookup = function (user) {
            userGateway.lookup(user).then(function (data) {
              $scope.statusIsOk = true;
              $scope.statusMessage = data;
            }, function (data) {
              $scope.statusIsOk = false;
              $scope.statusMessage = data;
            });
          };

          $scope.register = function (user) {
            userGateway.register(user).then(function (data) {
              $scope.statusIsOk = true;
              $scope.statusMessage = data;
            }, function (data) {
              $scope.statusIsOk = false;
              $scope.statusMessage = data;
            });
          };
        }])
        .controller('accountCtrl', ['$scope', function ($scope) {
        }])
        .controller('inputCtrl', ['$scope', function ($scope) { // for test page
          $scope.user = {name: 'guest', last: 'visitor'};
        }]);
