/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var bankControllers = angular.module('bankControllers', ['Gateway']);

bankControllers.controller('RegisterController',
        ['$scope', 'UserGateway',
          function ($scope, UserGateway) {

            $scope.validateAndRegister = function (username, password, repassword) {

              if (isLengthValid(username) && isLengthValid(password) && isLengthValid(repassword)) {
                UserGateway.register(username, password, repassword).then(function (data) {
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
