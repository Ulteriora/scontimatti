import { BaseEntity } from './../../shared';

export const enum StatoOrdine {
    'ATTESA_PAGAMENTO',
    'PAGATO',
    'SPEDITO'
}

export class ListaScontiMatti implements BaseEntity {
    constructor(
        public id?: number,
        public progressivo?: number,
        public numeroOrdine?: number,
        public dataOrdine?: any,
        public pagamentoEffettivo?: number,
        public totaleCompensare?: number,
        public totaleCompentato?: number,
        public statoOrdine?: StatoOrdine,
    ) {
    }
}
