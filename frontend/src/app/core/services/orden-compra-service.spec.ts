import { TestBed } from '@angular/core/testing';
import { OrdenCompraService } from './orden-compra-service';

describe('OrdenCompraService', () => {
  let service: OrdenCompraService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrdenCompraService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
