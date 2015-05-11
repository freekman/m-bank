/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var bankControllers = angular.module('bankControllers', ['formValidator', 'HttpModule']);

bankControllers.controller('RegisterController',
        ['$scope', 'fieldValidation', 'HttpRequest',
          function ($scope, fieldValidation, HttpRequest) {

            $scope.validateAndRegister = function (username, password, repassword) {

              if (fieldValidation(username) && fieldValidation(password) && fieldValidation(repassword)) {
                HttpRequest
                        .send('POST', 'register/new', {
                          username: username,
                          password: password,
                          repassword: repassword
                        })
                        .then(function (data) {
                          $scope.statusIsOk = data.valid;
                          $scope.statusMessages = data.messages;
                        }, function (data) {
                          $scope.statusIsOk = false;
                          $scope.statusMessages = ['Fields must be at least 3 chars.'];
                        });
                //registerNewUser(username, password, repassword);
              } else {
                $scope.statusIsOk = false;
                $scope.statusMessages = ['Fields must be at least 3 chars.'];
              }
            };


            //function registerNewUser(username, password, repassword) {
            //  $http
            //          .post('/register/new', {username: username, password: password, repassword: repassword})
            //          .success(function (data) {
            //            $scope.statusIsOk = data.valid;
            //            $scope.statusMessages = data.messages;
            //          })
            //          .error(function () {
            //            $scope.statusIsOk = false;
            //            $scope.statusMessages = ['Sorry, something went wrong ;('];
            //          });
            //}

          }]);