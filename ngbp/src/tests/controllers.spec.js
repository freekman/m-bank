/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */


describe('Controllers', function () {

  beforeEach(module('bankCtrl'));

  describe('bankCtrl', function () {
    var scope, userGateway;
    var deferred;

    beforeEach(inject(function ($q) {
      deferred = $q.defer();
      userGateway = {register: jasmine.createSpy().andReturn(deferred.promise)};

      inject(function ($rootScope, $controller) {
        scope = $rootScope.$new();
        $controller('registerCtrl', {$scope: scope, userGateway: userGateway});
      });

    }));

    it('should receive server response for successful registration', function () {
      scope.register('test', 'test', 'test');

      expect(userGateway.register).toHaveBeenCalledWith('test', 'test', 'test');

      deferred.resolve({valid: true, messages: ['Registration successful']});
      scope.$digest();

      expect(scope.statusIsOk).toBeTruthy();
      expect(scope.statusMessages).toEqual(['Registration successful']);
    });

    it('should not register if fields are undefined', function () {
      expect(scope.statusIsOk).toBeUndefined();
      expect(scope.statusMessages).toBeUndefined();
      scope.register();
      expect(scope.statusIsOk).toBeFalsy();
      expect(scope.statusMessages).toEqual(['Fields must be at least 3 chars.']);
    });

  });

});