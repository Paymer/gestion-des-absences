export function route ($routeProvider, $locationProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider
    .when('/', {
        template: '<menu-component></menu-component>' + '<accueil-component></accueil-component>'
    })
    .when('/absence', {
        template: '<menu-component></menu-component>' + '<visualisation-absence-component></<visualisation-absence-component>'
     })
    .when('/absence/demande',{
        template: '<menu-component></menu-component>' + '<demande-absence-component></demande-absence-component>'
    })
    .when('/absence/modification', {
		template: '<menu-component></menu-component>' + '<modif-absence-component></modif-absence-component>'
	})
    .when('/absence/validation', {
		template: '<menu-component></menu-component>' + '<validation-absence-component></validation-absence-component>'
    })
    .when('/absence/planning',{
        template: '<menu-component></menu-component>' + '<planning-absence-component></planning-absence-component>'
    })
    .when('/ferie', {
        template: '<menu-component></menu-component>' + '<visualisation-ferie-component></<visualisation-ferie-component>'
     })
    .when('/ferie/creation', {
        template: '<menu-component></menu-component>' + '<creation-ferie-component></<creation-ferie-component>'
     })
    .when('/rapport', {
        template: '<menu-component></menu-component>' + '<rapport-component></<rapport-component>'
     })
    .when('/rapport/vdjc', {
        template: '<menu-component></menu-component>' + '<vue-dep-jour-collab-component></<vue-dep-jour-collab-component>'
     })
    .when('/connexion', {
		template: '<connexion-component></connexion-component>'
	})
    .otherwise({
        redirectTo: '/connexion'
    });

}
