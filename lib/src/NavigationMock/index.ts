
export const MockNavigation = () => {
    jest.mock('./../adapters/NativeCommandsSender', () => require('./NativeCommandsSender.tsx'));
    jest.mock('./../adapters/NativeEventsReceiver', () => require('./NativeEventsReceiver.ts'));
};

export const getApplication = () => require('./Application').Application;

export const VISIBLE_SCREEN = 'VISIBLE_SCREEN';
export const VISIBLE_OVERLAY = 'VISIBLE_OVERLAY';