/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
var bankModule = angular.module('bankCtrl', ['gateway']);

bankModule.controller('registerCtrl', ['$scope', 'userGateway', function ($scope, userGateway) {
  'use strict';

  $scope.lookup = function (username) {
    userGateway.lookup(username).then(function (data) {
      $scope.statusIsOk = data.valid;
      $scope.statusMessages = data.messages;
    });
  };

  $scope.register = function (user) {
    userGateway.register(user).then(function (data) {
      $scope.statusIsOk = data.valid;
      $scope.statusMessages = data.messages;
    });
  };
}])
        .controller('inputCtrl', ['$scope', function ($scope) { // for test page
          $scope.user = {name: 'guest', last: 'visitor'};
        }]);
