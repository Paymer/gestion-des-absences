export function route ($routeProvider, $locationProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider
    .when('/', {
        template: '<menu-component></menu-component>' + '<accueil></accueil>'
    })
    .when('/absence/demande',{
        template: '<menu-component></menu-component>' + '<demande-absence></demande-absence>'
    })
    .when('/absence', {
        template: '<menu-component></menu-component>' + '<visualisation-absence-component></<visualisation-absence-component>>'
     })
    .when('/connexion', {
		template: '<connexion-component></connexion-component>'
	})
    .when('/absence/modification', {
		template: '<menu-component></menu-component>' + '<modif-absence-component></modif-absence-component>'
	})
    .otherwise({
        redirectTo: '/connexion'
    });

}
