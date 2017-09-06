export function route ($routeProvider, $locationProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider
    .when('/', {
       // template: '<menu-employe-component></menu-employe-component>' + '<accueil></accueil>'
       template: '<accueil></accueil>'
    //    template: '<menu-admin-component></menu-admin-component>' + '<accueil></accueil>'
       // template: '<menu-manager-component></menu-manager-component>' + '<accueil></accueil>'
    })
    .when('/absence/demande',{
        template: '<menu-employe-component></menu-employe-component>' + '<demande-absence></demande-absence>'
    })
    .when('/absence', {
        template: '<visualisation-absence-component></<visualisation-absence-component>>'
     })
    .when('/connexion', {
		template: '<connexion-component></connexion-component>'
	})
    .otherwise({
        redirectTo: '/'
    });

}
