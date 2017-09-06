export function route ($routeProvider, $locationProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider
    .when('/', {
        template: '<menu-employe-component></menu-employe-component>' + '<accueil></accueil>'
    })
    .when('/absence/demande',{
        template: '<menu-employe-component></menu-employe-component>' + '<demande-absence></demande-absence>'
    })
    .otherwise({
        redirectTo: '/'
    });

}
