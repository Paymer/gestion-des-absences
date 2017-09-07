
describe('test demande absence $http PUT', () => {
    beforeEach(() => {
        angular.mock.module('app')
    })

    // injection du mock $httpBackend
    it(`verifie l'envoie d'un PUT d'une absence `, angular.mock.inject(($httpBackend, modifAbsenceService)=> {

        const absence = {"dateDebut":"2017-09-13","dateFin":"2017-09-28","type":"CONGES_SANS_SOLDE","matriculeEmploye":"M002","motif":"La tests motif"};

        const urlPut = API_URL+ `/absence/modification`
        // définition du comportement attendu de $http
        $httpBackend.whenPUT(urlPut).respond(200,{});
       
        modifAbsenceService.confirmeEnvoiAbsence(absence)

        $httpBackend.expectPUT(urlPut, absence);
        
        $httpBackend.flush(); // déclenche les réponses HTTP



    }))
})