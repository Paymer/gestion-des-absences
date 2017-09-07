describe('test demande absence $http Post', () => {
    beforeEach(() => {
        angular.mock.module('app')
    })
    // injection du mock $httpBackend
    it(`verifie l'envoie d'un Post d'une absence `, angular.mock.inject(($httpBackend, demandeAbsenceService,apiUrls)=> {

        const absence = {"dateDebut":"2017-09-13","dateFin":"2017-09-28","type":"CONGES_SANS_SOLDE","matriculeEmploye":"M002","motif":"La tests motif"};

        const urlPost = `${apiUrls.demandeAbsence}?save=%7B%22method%22:%22POST%22%7D`
        // définition du comportement attendu de $http
        $httpBackend.whenPOST(urlPost).respond(200,{});
       
        demandeAbsenceService.confirmeEnvoiAbsence(absence)

        $httpBackend.expectPOST(urlPost,absence);
        
        $httpBackend.flush(); // déclenche les réponses HTTP



    }))
})