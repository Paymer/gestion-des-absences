export function route ($routeProvider, $locationProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider
    .when('/', {
        template: '<accueil></accueil>'
    })
    .when('/absence', {
        template: '<visualisation-absence-component></visualisation-absence-component>'
    })
    .otherwise({
        redirectTo: '/'
    });

}
