/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */


describe('Controllers', function () {


  beforeEach(module('bankControllers'));

  beforeEach(function () {
    this.addMatchers({
      toEqualData: function (expected) {
        return angular.equals(this.actual, expected);
      }
    });
  });

  describe('bankControllers', function () {
    var scope, ctrl, $httpBackend;

    beforeEach(inject(function (_$httpBackend_, $rootScope, $controller) {
      scope = $rootScope.$new();
      $httpBackend = _$httpBackend_;
      ctrl = $controller('RegisterController', {$scope: scope});

      $httpBackend.expectPOST('register/new').respond(200, {valid: 'true', messages: ['Registration successful']});
    }));

    it('should receive server response for successful registration', function () {
      expect(scope.statusIsOk).toBeUndefined();
      expect(scope.statusMessages).toBeUndefined();
      scope.validateAndRegister('test', 'test', 'test');
      $httpBackend.flush();
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