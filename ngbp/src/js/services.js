/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */

var serviceModule = angular.module('formValidator', []);

serviceModule.service('fieldValidation', function () {
  return function (field) {
    return !(field == null || field.length < 3);
  }
});
