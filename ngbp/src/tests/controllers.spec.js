/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */


describe('Controllers', function () {

  beforeEach(module('bankCtrl'));

  describe('bankCtrl', function () {
    var scope, userGateway;
    var deferred;

    var dummyUser = {username: 'Dummy', password: 'pass', repassword: 'pass'};

    beforeEach(inject(function ($q) {
      deferred = $q.defer();
      userGateway = {register: jasmine.createSpy().andReturn(deferred.promise)};

      inject(function ($rootScope, $controller) {
        scope = $rootScope.$new();
        $controller('registerCtrl', {$scope: scope, userGateway: userGateway});
      });

    }));


    it('should receive server response for successful registration', function () {
      scope.register(dummyUser);
      expect(userGateway.register).toHaveBeenCalledWith(dummyUser);

      deferred.resolve({valid: true, messages: ['Registration successful']});
      scope.$digest();

      expect(scope.statusIsOk).toBeTruthy();
      expect(scope.statusMessages).toEqual(['Registration successful']);
    });

  });

});