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

      deferred.resolve('Registration successful');
      scope.$digest();

      expect(scope.statusIsOk).toBeTruthy();
      expect(scope.statusMessage).toEqual('Registration successful');
    });

    it('should lookup free username on server', function () {
      scope.lookup(dummy);
      expect(userGateway.lookup).toHaveBeenCalledWith(dummy);

      deferred.resolve('Username is free');
      scope.$digest();

      expect(scope.statusIsOk).toBeTruthy();
      expect(scope.statusMessage).toEqual('Username is free');
    });

  });


  describe('accountCtrl', function () {
    var scope, accGateway, deferred;

    beforeEach(module('bankCtrl'));

    beforeEach(inject(function ($q) {
      deferred = $q.defer();

      accGateway = {
        deposit: jasmine.createSpy().andReturn(deferred.promise),
        withdraw: jasmine.createSpy().andReturn(deferred.promise)
      };

      inject(function ($rootScope, $controller) {
        scope = $rootScope.$new();
        $controller('accountCtrl', {$scope: scope, accGateway: accGateway});
      });

    }));


    it('should perform deposit request and update balance', function () {
      var amount = 15;
      scope.deposit(amount);
      expect(accGateway.deposit).toHaveBeenCalledWith(amount);

      deferred.resolve(amount);
      scope.$digest();

      expect(scope.balance).toEqual(amount);
      expect(scope.statusIsOk).toBeTruthy();
      expect(scope.statusMessage).toEqual("Operation successful.");
    });

    it('should notify for failed deposit request', function () {
      var amount = 'five';
      scope.deposit(amount);
      expect(accGateway.deposit).toHaveBeenCalledWith(amount);

      deferred.reject('Operation failed.');
      scope.$digest();

      expect(scope.statusIsOk).toBeFalsy();
      expect(scope.statusMessage).toEqual('Operation failed.');
    });

    it('should perform withdraw request and update balance', function () {
      var amount = 3;
      scope.withdraw(amount);
      expect(accGateway.withdraw).toHaveBeenCalledWith(amount);

      deferred.resolve(1);
      scope.$digest();


      expect(scope.balance).toEqual(1);
      expect(scope.statusIsOk).toBeTruthy();
      expect(scope.statusMessage).toEqual("Operation successful.");
    });

    it('should notify for failed withdraw request', function () {
      var amount = 'hello';

      scope.withdraw(amount);
      expect(accGateway.withdraw).toHaveBeenCalledWith(amount);

      deferred.reject('Operation failed.');
      scope.$digest();

      expect(scope.statusIsOk).toBeFalsy();
      expect(scope.statusMessage).toEqual('Operation failed.');
    });

  });

});