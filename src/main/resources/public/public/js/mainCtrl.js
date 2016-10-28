var app = angular.module('phonebook');

app.controller('mainCtrl',function($scope, $location, authStore){
	$scope.showLogOut = function(){
		return authStore.isAuth();
	}
	$scope.signOut = function(){
		authStore.signOut();
		$location.path('/signin');
	}
});