export function route ($routeProvider, $locationProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider
    .when('/', {
        template: '<menu-employe-component></menu-employe-component>' + '<accueil></accueil>'
    })
    .otherwise({
        redirectTo: '/'
    });

}
