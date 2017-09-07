describe('test validation absence controller', () => {
	beforeEach(() => {
		angular.mock.module('app')
	})

	it('test init', angular.mock.inject($componentController => {

		const promess = new Promise(resolve => resolve([
				{id: 2}
			]))

		const validationAbsenceController = $componentController(
			'validationAbsenceComponent', {validationAbsenceService: {
					findAll: () => promess
				}})
		validationAbsenceController.$onInit()

		promess.then(() => {
			expect(validationAbsenceController.absences.length)
				.toBe(1)
		})


	}))

	it('test setAbsence', angular.mock.inject(($componentController) => {

		const promessStatut = new Promise(resolve => resolve({success: true}))
		const promessFind = new Promise(resolve => resolve([
				{id: 2}
			]))

		const validationAbsenceController = $componentController(
			'validationAbsenceComponent', {validationAbsenceService: {
					setStatutDemande: () => promessStatut,
					findAll: () => promessFind
				}})

		validationAbsenceController.setAbsence(2, true)

		promessFind.then(() => {
			expect(validationAbsenceController.errorMaj)
				.toBe(false)
			expect(validationAbsenceController.errorServeur)
				.toBe(false)
			expect(validationAbsenceController.absences.length)
				.toBe(1)
		})

	}))

})