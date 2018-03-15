/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ScontimattiTestModule } from '../../../test.module';
import { OrdineScontiMattiComponent } from '../../../../../../main/webapp/app/entities/ordine-sconti-matti/ordine-sconti-matti.component';
import { OrdineScontiMattiService } from '../../../../../../main/webapp/app/entities/ordine-sconti-matti/ordine-sconti-matti.service';
import { OrdineScontiMatti } from '../../../../../../main/webapp/app/entities/ordine-sconti-matti/ordine-sconti-matti.model';

describe('Component Tests', () => {

    describe('OrdineScontiMatti Management Component', () => {
        let comp: OrdineScontiMattiComponent;
        let fixture: ComponentFixture<OrdineScontiMattiComponent>;
        let service: OrdineScontiMattiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ScontimattiTestModule],
                declarations: [OrdineScontiMattiComponent],
                providers: [
                    OrdineScontiMattiService
                ]
            })
            .overrideTemplate(OrdineScontiMattiComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdineScontiMattiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdineScontiMattiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new OrdineScontiMatti(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ordines[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
