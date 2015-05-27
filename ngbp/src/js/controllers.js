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
        .controller('accountCtrl', ['$scope', 'accGateway', function ($scope, accGateway) {
          $scope.deposit = function (amount) {
            accGateway.deposit(amount).then(function (balance) {
                      $scope.balance = balance;
                    }, function (data) {
                      $scope.statusMessage = data;
                    }
            );
          };
        }]);