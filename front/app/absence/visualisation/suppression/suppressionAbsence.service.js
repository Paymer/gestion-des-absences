
export default class SuppressionAbsenceService {
    constructor($http, apiUrls, $window) {
        this.$http = $http;
        this.apiUrls = apiUrls;
        this.$window = $window;
    }

    supprimerAbsence(idAbsence) {
        this.$http.delete(this.apiUrls.suppressionAbsence + "/" + idAbsence)
        this.$window.location.reload();
    }
}