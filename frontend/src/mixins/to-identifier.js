export const toIdentifier = {
  methods: {
    toIdentifier: function (value) {
      return value
      .toLowerCase()
      .replace('ä', 'ae')
      .replace('ö', 'oe')
      .replace('ü', 'ue')
      .replace('ß', 'ss')
      .replace(/[^\w ]+/g, '')
      .replace(/ +/g, '-');
    }
  }
};
