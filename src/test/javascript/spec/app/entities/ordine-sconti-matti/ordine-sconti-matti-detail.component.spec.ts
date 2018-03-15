/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ScontimattiTestModule } from '../../../test.module';
import { OrdineScontiMattiDetailComponent } from '../../../../../../main/webapp/app/entities/ordine-sconti-matti/ordine-sconti-matti-detail.component';
import { OrdineScontiMattiService } from '../../../../../../main/webapp/app/entities/ordine-sconti-matti/ordine-sconti-matti.service';
import { OrdineScontiMatti } from '../../../../../../main/webapp/app/entities/ordine-sconti-matti/ordine-sconti-matti.model';

describe('Component Tests', () => {

    describe('OrdineScontiMatti Management Detail Component', () => {
        let comp: OrdineScontiMattiDetailComponent;
        let fixture: ComponentFixture<OrdineScontiMattiDetailComponent>;
        let service: OrdineScontiMattiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ScontimattiTestModule],
                declarations: [OrdineScontiMattiDetailComponent],
                providers: [
                    OrdineScontiMattiService
                ]
            })
            .overrideTemplate(OrdineScontiMattiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdineScontiMattiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdineScontiMattiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new OrdineScontiMatti(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ordine).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
