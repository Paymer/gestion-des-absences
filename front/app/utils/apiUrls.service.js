const apiUrls = {
    absence : API_URL + '/absence',
    suppressionAbsence : API_URL + '/absence/suppression',
	validationAbsence : API_URL + '/absence/validation',
    demandeAbsence : API_URL + '/absence/demande',
    modifAbsence : API_URL + '/absence/modification/:absenceId',
	
    ferie : API_URL + '/ferie',
    suppressionFerie : API_URL + '/ferie/suppression',
    demandeFerie : API_URL + '/ferie/demande',
    modifFerie : API_URL + '/ferie/modification/:ferieId',

    connexion: API_URL + '/connexion',
    congesEtRtt: API_URL + '/conges-et-rtt',

    mission: 'https://api-missions.cleverapps.io/missions/lister',
    
	message: API_URL + '/message'
}

export default apiUrls