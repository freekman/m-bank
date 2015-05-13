/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */


describe('Controllers', function () {

  beforeEach(module('bankControllers'));

  describe('bankControllers', function () {
    var scope, ctrl, UserGateway;
    var dummyPromise = {value: true, messages: ['Is ok']};

    var deferred;

    beforeEach(inject(function ($q) {
      deferred = $q.defer();
      UserGateway = {register: jasmine.createSpy().andReturn(deferred.promise)};

      inject(function ($rootScope, $controller) {
        scope = $rootScope.$new();
        $controller('RegisterController', {$scope: scope, UserGateway: UserGateway});
      });

    }));

    it('should receive server response for successful registration', function () {
      scope.validateAndRegister('test', 'test', 'test');

      expect(UserGateway.register).toHaveBeenCalledWith('test', 'test', 'test');

      deferred.resolve({valid: true, messages: ['Registration successful']});
      scope.$digest();

      expect(scope.statusIsOk).toBeTruthy();
      expect(scope.statusMessages).toEqual(['Registration successful']);
    });

    it('should not register if fields are undefined', function () {
      expect(scope.statusIsOk).toBeUndefined();
      expect(scope.statusMessages).toBeUndefined();
      scope.validateAndRegister();
      expect(scope.statusIsOk).toBeFalsy();
      expect(scope.statusMessages).toEqual(['Fields must be at least 3 chars.']);
    });

  });

});