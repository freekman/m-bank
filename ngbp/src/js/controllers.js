/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var bankCtrl = angular.module('bankCtrl', ['gateway']);

bankCtrl.controller('registerCtrl',
        ['$scope', 'userGateway',
          function ($scope, userGateway) {

            $scope.register = function (username, password, repassword) {

              if (isLengthValid(username) && isLengthValid(password) && isLengthValid(repassword)) {
                userGateway.register(username, password, repassword).then(function (data) {
                  $scope.statusIsOk = data.valid;
                  $scope.statusMessages = data.messages;
                });
              } else {
                $scope.statusIsOk = false;
                $scope.statusMessages = ['Fields must be at least 3 chars.'];
              }
            };
          }]);

function isLengthValid(field) {
  return !(field == null || field.length < 3);
}
