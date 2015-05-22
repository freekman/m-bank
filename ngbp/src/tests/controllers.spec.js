/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
describe('Controllers', function () {
  'use strict';

  beforeEach(module('bankCtrl'));

  describe('bankCtrl', function () {
    var scope, userGateway, deferred;

    var dummy = {username: 'Dummy', password: 'pass', repassword: 'pass'};

    beforeEach(inject(function ($q) {
      deferred = $q.defer();

      userGateway = {
        lookup: jasmine.createSpy().andReturn(deferred.promise),
        register: jasmine.createSpy().andReturn(deferred.promise)
      };

      inject(function ($rootScope, $controller) {
        scope = $rootScope.$new();
        $controller('registerCtrl', {$scope: scope, userGateway: userGateway});
      });
    }));

    it('should receive server response for successful registration', function () {
      scope.register(dummy);
      expect(userGateway.register).toHaveBeenCalledWith(dummy);

      deferred.resolve({valid: true, messages: ['Registration successful']});
      scope.$digest();

      expect(scope.statusIsOk).toBeTruthy();
      expect(scope.statusMessages).toEqual(['Registration successful']);
    });

    it('should lookup free username on server', function () {
      scope.lookup(dummy);
      expect(userGateway.lookup).toHaveBeenCalledWith(dummy);

      deferred.resolve({valid: true, messages: ['Username is free']});
      scope.$digest();

      expect(scope.statusIsOk).toBeTruthy();
      expect(scope.statusMessages).toEqual(['Username is free']);
    });

  });

});