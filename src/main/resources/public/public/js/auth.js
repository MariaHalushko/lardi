var module = angular.module("authService",[]);

module.factory("authStore", function($http,$location){

	var authURL = "/oauth/token";
	var auth = false;

	return {

		signIn : function (login, pass, success, error) {
 			var req = {
 				method: 'POST',
 				url: authURL,
 				headers: {
   					"Authorization": "Basic " + btoa("clientapp" + ":" + "123456"),
   					'Content-Type': 'application/x-www-form-urlencoded'
 				},
 				data: $.param({ 
 					password : pass,
                	username : login,
                	grant_type: "password",
                	scope: "read write",
                	client_secret:"123456",
                	client_id: "clientapp" })
			}


      var authProc = $http(req);

      authProc.success(function(data, status, headers, config) {
        $http.defaults.headers.common.Authorization= 'Bearer ' + data.access_token;
        auth = true;
        success();
      });

      authProc.error(function(data, status, headers, config) {
        error(data.error)
    });
    	},

    	isAuth: function () {
  			return auth;
  		},

  		signOut: function(){
  			auth = false;
  			account = {}
  			$http.defaults.headers.common.Authorization = '';
  		}
	}

})