describe('test validation absence service', () => {
	beforeEach(() => {
		angular.mock.module('app')
	})

	it('test findAll', angular.mock.inject(($httpBackend,
		validationAbsenceService, apiUrls) => {

		validationAbsenceService.connexion = {
			getMatricule: () => "M01"
		}

		const urlFind = apiUrls.validationAbsence + '/' + validationAbsenceService.connexion.getMatricule()
		
		$httpBackend.whenGET(urlFind)
		.respond({
				"success": true
		});

		validationAbsenceService.findAll()
			.then(resp => {
				expect(resp.success).toBe(true)
			})

		$httpBackend.flush();
	}))
	
	it('test setStatutDemande', angular.mock.inject(($httpBackend, validationAbsenceService, apiUrls) => {
		
		const urlUpdate = apiUrls.validationAbsence + '/' + validationAbsenceService.connexion.getMatricule() + '/' + 2 + '/' + true
		
		$httpBackend.whenPATCH(urlUpdate)
		.respond({
				"success": true
		});
		
		validationAbsenceService.setStatutDemande(2, true).then(resp => {
			expect(resp.success).toBe(true)
		})
		
		$httpBackend.flush();
	}))

})