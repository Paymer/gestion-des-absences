export function route ($routeProvider, $locationProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider
    .when('/', {
        template: '<menu-component></menu-component>' + '<accueil></accueil>'
    })
	.when('/connexion', {
		template:'<connexion-component></connexion-component>'
	})
    .when('/absence', {
        template: '<menu-component></menu-component>' + '<visualisation-absence-component></<visualisation-absence-component>>'
     })
    .otherwise({
        redirectTo: '/'
    });

}
