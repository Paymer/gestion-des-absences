
export default class SuppressionAbsenceService {
    constructor($http, apiUrls) {
        this.$http = $http;
        this.apiUrls = apiUrls;
    }

    supprimerAbsence(idAbsence) {
        this.$http.delete(this.apiUrls.suppressionAbsence + "/" + idAbsence)
    }
}