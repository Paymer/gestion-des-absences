const apiUrls = {
    absence: API_URL + '/absence',
    suppressionAbsence: API_URL + '/absence/suppression',
	validationAbsence: API_URL + '/absence/validation',
    demandeAbsence: API_URL + '/absence/demande',
    modifAbsence: API_URL + '/absence/modification/:absenceId',

    collaborateur: API_URL + '/collaborateur',
    departement: API_URL + '/departement',
	
    ferie : API_URL + '/ferie',
    suppressionFerie : API_URL + '/ferie/suppression',
    modifFerie : API_URL + '/ferie/modification',

    connexion: API_URL + '/connexion',
    congesEtRtt: API_URL + '/conges-et-rtt',

    histogramme: API_URL + '/histogramme',
    mission: 'https://api-missions.cleverapps.io/missions/lister',
    
	message: API_URL + '/message'
}

export default apiUrls