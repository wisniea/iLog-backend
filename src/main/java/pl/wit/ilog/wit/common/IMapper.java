package pl.wit.ilog.wit.common;

public interface IMapper<SRC,DST> {
    DST map(SRC src);
}
