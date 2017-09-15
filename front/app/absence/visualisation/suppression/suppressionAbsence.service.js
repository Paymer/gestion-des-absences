
export default class SuppressionAbsenceService {
    constructor($http, apiUrls) {
        this.$http = $http;
        this.apiUrls = apiUrls;
    }

    supprimerAbsence(idAbsence) {
        return this.$http.delete(this.apiUrls.suppressionAbsence + "/" + idAbsence)
    }
}