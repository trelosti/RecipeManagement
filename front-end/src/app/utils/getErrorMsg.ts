function getErrorMsg(error: unknown): string | undefined {
  return (
    (typeof error === 'object' &&
      error != null &&
      'status' in error &&
      'data' in error &&
      JSON.stringify(error.data)) ||
    undefined
  );
}

export default getErrorMsg;
