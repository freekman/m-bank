/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var bankCtrl = angular.module('bankCtrl', ['gateway']);

bankCtrl
        .controller('registerCtrl', ['$scope', 'userGateway', function ($scope, userGateway) {
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

