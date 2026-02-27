import { TestBed } from '@angular/core/testing';

import { UserBadge } from './user-badge';

describe('UserBadge', () => {
  let service: UserBadge;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserBadge);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
