/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var bankCtrl = angular.module('bankCtrl', ['gateway']);

bankCtrl
        .controller('registerCtrl', ['$scope', 'userGateway', function ($scope, userGateway) {
          $scope.user = {name: '', password: '', repassword: ''};
          $scope.isUserValid = false;

          $scope.register = function (isValid) {
            if (isValid) {
              userGateway.register($scope.user.name, $scope.user.password, $scope.user.repassword).then(function (data) {
                $scope.statusIsOk = data.valid;
                $scope.statusMessages = data.messages;
              });
            } else {
              $scope.statusIsOk = false;
              $scope.statusMessages = ['Fields must be at least 3 chars.'];
            }
          };
        }])
        .controller('inputCtrl', ['$scope', function ($scope) {
          $scope.user = {name: 'guest', last: 'visitor'};
        }]);

