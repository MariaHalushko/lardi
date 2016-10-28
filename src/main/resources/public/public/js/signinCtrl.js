var app = angular.module('phonebook');

app.controller('signinCtrl',function($scope, $http, $location, authStore){

	$scope.user = {
		login:"",
		password:""
	}

	$scope.error = "";

	$scope.goSignUp = function(){
		$location.path('/signup');
	}

	$scope.signIn = function(){
		authStore.signIn($scope.user.login,$scope.user.password,function(){
			$location.path('/');
		}, function(error){
			if (error="invalid_grant") {
				$scope.error = "Invalid login or password";
			}
			
		});
	}
});