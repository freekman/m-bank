/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
var gatewayModule = angular.module('gateway', ['httpModule']);

gatewayModule
        .service('userGateway', ['httpRequest', function (httpRequest) {
          'use strict';
          return {
            lookup: function (user) {
              return httpRequest
                      .send('GET', '/r/register?username=' + user.name, {});
            },
            register: function (user) {
              return httpRequest
                      .send('POST', '/r/register',
                      {
                        username: user.name,
                        password: user.password,
                        repassword: user.repassword
                      });
            }
          };
        }])
        .service('accGateway', ['httpRequest', function (httpRequest) {
          return {
            deposit: function (amount) {
              return httpRequest.send('POST', '/r/balance/deposit', {amount: amount});
            },
            withdraw: function (amount) {
              return httpRequest.send('POST', '/r/balance/withdraw', {amount: amount});
            },
            fetchUser: function () {
              return httpRequest.send('POST', '/r/balance/info', {});
            }
          };
        }]);