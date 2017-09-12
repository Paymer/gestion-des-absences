const apiUrls = {
    absence : API_URL + '/absence',
    suppressionAbsence : API_URL + '/absence/suppression',
	validationAbsence : API_URL + '/absence/validation',
    demandeAbsence : API_URL + '/absence/demande',
   
    modifAbsence : API_URL + '/absence/modification/:absenceId',

    connexion: API_URL + '/connexion',
    congesEtRtt: API_URL + '/conges-et-rtt'
}

export default apiUrls